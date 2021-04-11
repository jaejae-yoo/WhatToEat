import urllib.request
import json
import pandas as pd
from pandas.io.json import json_normalize
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import random
import socket

url = "http://openapi.seoul.go.kr:8088/API_KEY/json/LOCALDATA_072404/1/1000"

response = urllib.request.urlopen(url)
json_str = response.read().decode("utf-8")
json_object = json.loads(json_str)
df=pd.json_normalize(json_object['LOCALDATA_072404']['row'])
df = df[['BPLCNM','UPTAENM']]

count_vector = CountVectorizer(ngram_range=(1, 3))
c_vector_genres = count_vector.fit_transform(df['UPTAENM'])
gerne_c_sim = cosine_similarity(c_vector_genres, c_vector_genres).argsort()[:, ::-1]

def get_recommend_movie_list(df, food_title, top=20):
    food_index = df[df['BPLCNM'] == food_title].index.values

    # cosine similarity
    similarity_index = gerne_c_sim[food_index, :top].reshape(-1)
    similarity_index = similarity_index[similarity_index != food_index]
    result = df.iloc[similarity_index].sort_values('UPTAENM', ascending=False)[:10]
    return result

num = random.randrange(0,10)
restaurant =[]
for r in get_recommend_movie_list(df, food_title='시골밥상')['BPLCNM']:
    restaurant.append(r)

print(restaurant[num])
host = 'IP'
port = 8080

server_sock = socket.socket(socket.AF_INET)
server_sock.bind((host, port))
server_sock.listen(1)
print("기다리는 중..")
out_data = restaurant[num]

while True: #Android connection
    client_sock, addr = server_sock.accept()

    if client_sock:
        print('Connected by: ', addr)
        in_data = client_sock.recv(1024)
        print('rcv :', in_data.decode("utf-8"), len(in_data))

        for i in range(1) :
            client_sock.send(str(out_data).encode("utf-8"))
            print('send :', out_data)
            out_data = restaurant[num]


client_sock.close()
server_sock.close()