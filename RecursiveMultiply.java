from prefect import task, flow, get_run_logger
import requests

class MondayAPIError(Exception):
    """Custom exception for monday.com API errors."""
    pass

@task
def get_monday_item_ids(api_key, board_id):
    """
    Fetch all item IDs from a specific board on monday.com.

    This task makes a GraphQL API request to monday.com to retrieve all items from the specified board.
    It extracts and returns a list of item IDs. If the request fails or the response cannot be parsed,
    a custom exception (`MondayAPIError`) is raised.

    Parameters:
        api_key (str): The API key for authenticating with the monday.com API.
        board_id (str): The ID of the board from which to fetch items.

    Returns:
        list: A list of item IDs (as integers) from the specified board. If no items are found,
              an empty list is returned.

    Raises:
        MondayAPIError: If the API request fails or the response cannot be parsed.

    Example:
        ```python
        item_ids = get_monday_item_ids("your_api_key", "12345")
        print(item_ids)  # Output: [123, 456, 789]
        ```
    """
    url = "https://api.monday.com/v2"
    headers = {
        "Authorization": api_key,
        "Content-Type": "application/json"
    }
    
    query = """
    {
        boards(ids: %s) {
            items {
                id
            }
        }
    }
    """ % board_id
    
    try:
        response = requests.post(url, headers=headers, json={'query': query})
        response.raise_for_status()  # Raises an HTTPError for bad responses (4xx, 5xx)
        data = response.json()
        
        # Extract item IDs from the response
        items = data.get("data", {}).get("boards", [{}])[0].get("items", [])
        item_ids = [item["id"] for item in items]  # Extract IDs
        return item_ids
    except requests.exceptions.RequestException as e:
        # Raise a custom exception for API request failures
        raise MondayAPIError(f"Request failed: {e}")
    except (KeyError, IndexError) as e:
        # Raise a custom exception for JSON parsing errors
        raise MondayAPIError(f"Data parsing failed: {e}")

@flow
def monday_flow(api_key, board_id):
    """
    Prefect flow to fetch item IDs from monday.com and process them.

    This flow calls the `get_monday_item_ids` task to retrieve item IDs from a specified board.
    If the task fails, the error is logged, and an empty list is used as a fallback.

    Parameters:
        api_key (str): The API key for authenticating with the monday.com API.
        board_id (str): The ID of the board from which to fetch items.
    """
    try:
        item_ids = get_monday_item_ids(api_key, board_id)
        print(f"Fetched item IDs: {item_ids}")
    except MondayAPIError as e:
        logger = get_run_logger()
        logger.error(f"Error fetching item IDs: {e}")
        item_ids = []  # Fallback to an empty list
    print(f"Processed item IDs: {item_ids}")

# Run the flow
if __name__ == "__main__":
    api_key = "your_monday_api_key_here"
    board_id = "your_board_id_here"
    monday_flow(api_key, board_id)
