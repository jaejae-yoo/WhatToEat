import pymysql
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np

conn = pymysql.connect(host='localhost', user='jae', password='password',  db='restaurantlist', charset='utf8')
cursor = conn.cursor()
sql = "select * from visit"
df = pd.read_sql_query(sql, conn)
df = df[['name','review']]
x_data = list(df['review'])

vect = TfidfVectorizer()
x_data = vect.fit_transform(x_data)
cosine_similarity_matrix = (x_data * x_data.T)
dict = {}
for i, sim_list in enumerate(cosine_similarity_matrix.toarray()):
    sim_list = list(sim_list)
    for x in range(len(sim_list)):
        if int(sim_list[x]) == 1 or sim_list[x] >= 0.99:
            sim_list[x] = 0
            break
    dict[df['name'][i]] = df['name'][sim_list.index(max(sim_list))]
conn.close()

conn = pymysql.connect(host='localhost', user='jae', password='password',  db='restaurantlist', charset='utf8')
cursor = conn.cursor()
sql = "UPDATE `visit` SET `recommend` = %s WHERE name = %s"
for rs in dict:
    print(rs, dict[rs])
    cursor.execute(sql, (dict[rs], rs))
conn.close()