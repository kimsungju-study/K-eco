from Modules import *

def GetFirebase() :
    url = 'https://fir-register2-a1322-default-rtdb.firebaseio.com/'
    cred = credentials.Certificate("fir-register2-a1322-firebase-adminsdk-w094f-66fd377223.json")
    firebase_admin.initialize_app(cred, {'databaseURL' : url})
    
    return True


def SetupIndex(database) :
  # columns = list(list(database)[0].keys())
  columns = list(database[0].keys())
  idxList = columns[:]
  for i in range(len(idxList)) : 
    idxList[i] = idxList[i].split('_')[-1]

  return columns, idxList.count('tickets')

def dayRepacker(data) :
    pd.set_option('mode.chained_assignment',  None)
    data.replace({'day': {'Monday' : 1, 
                        'Tuesday': 2,
                        'Wednesday' : 3,
                        'Thursday' : 4,
                        'Friday' : 5,
                        'Saturday' : 6,
                        'Sunday' : 7}}, inplace = True)

def repacker(data, before, after) :
    tmp = data[[before]]
    data[after] = tmp
    del data[before]
    
def Labeling(data, col) :
    label_encoder = LabelEncoder()
    tmp = label_encoder.fit_transform(data[col])
    data[col] = tmp

def FirebaseUpdator(word, idx) :
  loc = "Labels/"
  dir = db.reference(loc)
  dir.update({idx : word})
  print(f"Firebase Updated : {word}")

def WordPacker(word) :
    Label_list = db.reference('Labels').get()
    if None in Label_list :
        Label_list.remove(None)

    Label_list.append(word)

    tfidf = TfidfVectorizer()
    tfidf_matrix = tfidf.fit_transform(Label_list)
    cosine_sim = cosine_similarity(tfidf_matrix, tfidf_matrix)

    idx = Label_list.index(word)

    sim_scores = list(enumerate(cosine_sim[idx]))
    sim_scores = sorted(sim_scores, key = lambda x: x[1], reverse=True)
    sim_scores = sim_scores[1]

    if sim_scores[1] != 0 :
        word_idx = int(sim_scores[0])
        most_similarWord = Label_list[word_idx]
    else :
        most_similarWord = word
        if word != 'N' :
            FirebaseUpdator(word, len(Label_list))

    word_idx = Label_list.index(most_similarWord)

    return word_idx

def TypeRepacker(data, column, type) :
    data[column] = data[column].astype(type)

def SetupData(key, columns) :
    dataList = []
    for lst in key :
        dataList.append(list(lst.values()))

    data = pd.DataFrame(dataList, columns = columns)
    return data

def GetData(data, operand = False, dataName = None) :
    data['month'] = data['date'].apply(lambda x: x.split('_')[1])
    data['date'] = data['date'].apply(lambda x: x.split('_')[2])

    for col in data.columns :
        if col.find('data') != -1 and col.find('tickets') == -1 :
            data[col] = data[col].apply(lambda x: WordPacker(x)).astype('object')

    dayRepacker(data)
    TypeRepacker(data, 'month', 'object')
    TypeRepacker(data, 'date', 'object')
    TypeRepacker(data, 'day', 'object')

    if operand :
        data['day'] = data['date'].apply(lambda x : datetime.date(2023, int(data['month']), int(data['date'])).weekday())
        data = data[ColumnsReader(dataName)]
    else :
        TypeRepacker(data, 'data_bak_tickets', 'float')
        TypeRepacker(data, 'data_sp_tickets', 'float')
        TypeRepacker(data, 'data_dup_tickets', 'float')
        TypeRepacker(data, 'foodwaste', 'float')
    
    print(" - Data SettedUP!")
    return data
    
def ColumnSelector(data) :
    def GetImportance(data, colList) :  
        model = RandomForestRegressor()
        model.fit(data[colList[:-1]], data[colList[-1]])
        feature_importance = list(model.feature_importances_)
        
        return feature_importance

    colList = list(data.columns)
    splitedList = []

    menus = []
    for col in colList :
        if col.find('tickets') != -1 :
            menus.append(col.split('_')[1])
    
    for menu in menus :
        tmpSplitedList = []
        for col in colList :
            if col.find(menu) != -1 :
                tmpSplitedList.append(col)
        splitedList.append(tmpSplitedList)

    for colList in splitedList :
        importances = GetImportance(data, colList)
        featureName = colList[0][:-2]

        mostImportantCol = colList[importances.index(max(importances))]
        for col in colList :
            if col.find('tickets') == -1 and col != mostImportantCol:
                del data[col]

    del data['foodwaste']

    return data

def ColumnsRearranger(data, dataName) :
    colList = list(data.columns)
    for col in colList :
        if col.find('menu') != -1 :
            colList.remove(col)
            colList.append(col)
    data = data[colList]

    f = open('ColumnDatas/' + dataName + '.txt', 'w')
    for col in list(data.columns) :
        if col.find('tickets') == -1 :
            f.write(str(col) + '\n')
    f.close()

    return data

def ColumnsReader(dataName) :
    colList = []

    f = open('ColumnDatas/' + dataName + '.txt', 'r')
    lines = f.readlines()
    for col in lines :
        colList.append(col.strip())
    f.close()

    return colList

def GetInfo(data, column) :
    r = 0
    for tmp in data[column] :
        r += tmp

    return data[column].max(), data[column].min(), r / len(data)

def DataSpliter(data, idx) :
    datas = []

    for target_idx in range(idx) :
        col = data.columns[target_idx]
        data_col = list(data.columns[idx:len(data.columns)])
        tmp_data = data[[col] + data_col]

        tmp_data = tmp_data[~tmp_data[col].isnull()]
        datas.append(tmp_data)

    return datas

def RMSLE(y_true, y_pred) :
    for i in range(0, len(y_pred)) :
        if y_pred[i] < 0 :
            y_pred[i] = 0

    log_true = np.log1p(y_true - y_true.min() + 1)
    log_pred = np.log1p(y_pred - y_pred.min() + 1)
    
    output = np.sqrt(np.mean((np.array(log_true) - np.array(log_pred))**2, axis = 0))
    
    return output

def GetTrain(data, operand) :
    typ = data.columns[0]
    def splitXY(data, typ) :
        dataset_X = []
        dataset_y = []

        tmp_data = data.sample(frac=1).reset_index(drop=True)
        tmp_data = tmp_data[tmp_data[typ] != 0]

        dataset_y = tmp_data[typ]

        del tmp_data[typ]
        dataset_X = tmp_data

        return dataset_X, dataset_y

    dataset_X, dataset_y = splitXY(data, typ)

    long_dataset_X = np.array(pd.concat([dataset_X, dataset_X, dataset_X], ignore_index = True))
    long_dataset_y = np.array(pd.concat([dataset_y, dataset_y, dataset_y], ignore_index = True))

    X_train, X_test, y_train, y_test = train_test_split(long_dataset_X, long_dataset_y, test_size = 0.2, shuffle = True)


    train_predict_history = []
    test_predict_history = []

    model = None
    parameters = None
    RMSLE_scorer = metrics.make_scorer(RMSLE, greater_is_better = False)

    if operand == 'Lin' :
        model = LinearRegression()

    elif operand == 'Lasso' :
        model = Lasso()
        parameters = {'alpha' : list(range(50, 150, 1))}
        model = GridSearchCV(estimator = model,
                            param_grid = parameters,
                            scoring = RMSLE_scorer,
                            )

    elif operand == 'Ridge' :
        model = Ridge()
        parameters = {'alpha' : list(range(0, 5000, 100))}
        model = GridSearchCV(estimator = model,
                            param_grid = parameters,
                            scoring = RMSLE_scorer,
                            )

    elif operand == 'DT' :
        model = DecisionTreeRegressor()
        parameters = {'max_depth' : list(range(1, len(dataset_X.columns)+1)),
                    'max_features' : list(range(1, len(dataset_X.columns)+1))}
        model = GridSearchCV(estimator = model,
                            param_grid = parameters,
                            scoring = RMSLE_scorer,
                            )

    elif operand == 'RF' :
        model = RandomForestRegressor(n_jobs = -1)
        parameters = {'n_estimators' : list(range(50, 300, 50)),
                    'max_depth' : list(range(1, len(dataset_X.columns) + 1))}

        model = GridSearchCV(estimator = model,
                            param_grid = parameters,
                            scoring = RMSLE_scorer,
                            )

    model.fit(X_train, y_train)

    if parameters != None :
        print(f'{operand} :\t{model.best_params_}')
        test_y = model.best_estimator_.predict(X_test)
        y_pred = model.best_estimator_.predict(X_train)

    else :
        test_y = model.predict(X_test)
        y_pred = model.predict(X_train)

    train_predict_history.append([y_pred, y_train])
    test_predict_history.append([test_y, y_test])

    R2score_history = r2_score(test_y, y_test)
    RMSLE_history = RMSLE(y_pred, y_train)

    return model, R2score_history, RMSLE_history, train_predict_history, test_predict_history

def SetupModels(datas, idx, colName) :
    RF_model, RF_model_r2score, RF_model_RMSLE, RF_model_train_predicts, RF_model_test_predicts = GetTrain(datas[idx], 'RF')
    joblib.dump(RF_model, f'./{colName}/RF_model.pkl')

    return [RF_model_r2score, RF_model_RMSLE, RF_model_train_predicts, RF_model_test_predicts]