import requests
url = 'http://localhost:8080/article/articleList'
data = {}
for i in range(60):
    response = requests.get(url, data=data)
    print(f'the {i}th time, response status code is: {response.status_code}')
