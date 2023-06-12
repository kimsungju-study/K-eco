from Functions import *

GetFirebase()

firebase = db.reference('Kangwon').get()
# firebase = list(firebase.values())

columns, idx = SetupIndex(firebase)

SettedData = SetupData(firebase, columns)
# SettedData = SettedData[:-3]
rawData = GetData(SettedData)

selectedData = ColumnSelector(rawData.copy())
data = ColumnsRearranger(selectedData, 'Kangwon')

datas = DataSpliter(data, idx)

BaekbanResults = SetupModels(datas, 0, 'Bowl')
BowlResults = SetupModels(datas, 1, 'Baekban')
SpecialResults = SetupModels(datas, 2, 'Special')

print('\n')
print(f'백반 R2_Score\t: {BaekbanResults[0]}')
print(f'백반 RMSLE\t: {BaekbanResults[1]}')
print('\n')
print(f'덮밥 R2_Score\t: {BowlResults[0]}')
print(f'덮밥 RMSLE\t: {BowlResults[1]}')
print('\n')
print(f'특선 R2_Score\t: {SpecialResults[0]}')
print(f'특선 RMSLE\t: {SpecialResults[1]}')
print('\n')