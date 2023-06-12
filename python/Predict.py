from ServerFunctions import *

GetFirebase()

# testData = {'date' : '2023_05_23',
#             'day' : 'Tuesday',
#             'data_dup_menu_3' : '된장제육불고기',
#             'data_bak_menu_1' : '불닭마요덮밥',
#             'data_sp_menu_1' : '뚝배기감자탕',
#             'data_sp_menu_2' : '뚝배기감자탕'}

testData = {'date' : '2023_05_25',
            'day' : 'Thursday',
            'data_dup_menu_1' : '제육덮밥',
            'data_dup_menu_2' : '제육덮밥',
            'data_dup_menu_3' : '제육덮밥',
            'data_dup_menu_4' : '제육덮밥',
            'data_dup_menu_5' : '제육덮밥',
            'data_dup_menu_6' : '제육덮밥',
            'data_sp_menu_1' : '돈가스김치나베',
            'data_sp_menu_2' : '돈가스김치나베',
            'data_bak_menu_1' : '언양식불고기'}

# testData = {'date' : '2023_05_31',
#             'day' : 'Wednesday',
#             'data_dup_menu_3' : '제육고기떡볶이',
#             'data_bak_menu_1' : '불닭마요덮밥',
#             'data_sp_menu_1' : '돈가스김치나베',
#             'data_sp_menu_2' : '돈가스김치나베'}

# testData = {'date' : '2023_06_02',
#             'day' : 'Friday',
#             'data_dup_menu_3' : '닭가슴살샐러드',
#             'data_bak_menu_1' : '비빔밥',
#             'data_sp_menu_1' : 'N',
#             'data_sp_menu_2' : 'N'}

print(getModel(testData))