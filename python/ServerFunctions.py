import joblib
import pandas as pd
from Functions import *

def getModel(data) :
        loaded_model_Baekban = joblib.load('./Baekban/RF_model.pkl')
        loaded_model_Special = joblib.load('./Special/RF_model.pkl')
        loaded_model_Bowl = joblib.load('./Bowl/RF_model.pkl')

        data = pd.DataFrame(data, index = [0])
        data = GetData(data, True, 'Kangwon')

        predictedBaekban = loaded_model_Baekban.predict(data.values)
        predictedSpecial = loaded_model_Special.predict(data.values)
        predictedBowl = loaded_model_Bowl.predict(data.values)

        returnValue = {'predictedBowl' : round(predictedBowl[0]),
                       'predictedBaekban' : round(predictedBaekban[0]),
                       'predictedSpecial' : round(predictedSpecial[0])}

        return returnValue