import requests

# Replace with your API key and board ID
API_KEY = "your_api_key"
BOARD_ID = 123456789  # Replace with your board ID
URL = "https://api.monday.com/v2"

# GraphQL Query
query = f"""
{{
  boards(ids: {BOARD_ID}) {{
    id
    name
    items {{
      id
      name
      column_values {{
        id
        title
        text
      }}
    }}
  }}
}}
"""

# Headers
headers = {
    "Authorization": API_KEY,
}

# Make the Request
response = requests.post(URL, headers=headers, json={"query": query})

# Handle Response
if response.status_code == 200:
    data = response.json()
    board = data.get("data", {}).get("boards", [])[0]
    print(f"Board Name: {board['name']}")
    for item in board["items"]:
        print(f"  Item Name: {item['name']}")
        for column in item["column_values"]:
            print(f"    Column: {column['title']} - Value: {column['text']}")
else:
    print("Error:", response.status_code, response.text)
