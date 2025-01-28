import requests

# Your Monday.com API Key
API_KEY = "your_monday_api_key"

# Monday.com API URL
URL = "https://api.monday.com/v2"

# GraphQL Query to Get Board Details
query = """
{
    boards {
        id
        name
        items {
            id
            name
            column_values {
                id
                title
                text
            }
        }
    }
}
"""

# Headers
headers = {
    "Authorization": API_KEY,
}

# Send Request
response = requests.post(
    URL,
    headers=headers,
    json={"query": query},
)

# Output the Response
if response.status_code == 200:
    data = response.json()
    boards = data.get("data", {}).get("boards", [])
    for board in boards:
        print(f"Board ID: {board['id']}, Name: {board['name']}")
        for item in board["items"]:
            print(f"  Item ID: {item['id']}, Name: {item['name']}")
            for column in item["column_values"]:
                print(f"    Column: {column['title']} - Value: {column['text']}")
else:
    print("Failed to fetch board details:", response.text)
