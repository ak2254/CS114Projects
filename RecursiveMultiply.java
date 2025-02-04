import asyncio
import aiohttp
from prefect import task, flow, get_run_logger

class MondayAPIError(Exception):
    """Custom exception for monday.com API errors."""
    pass

async def delete_items_batch(api_key, item_ids):
    """
    Delete multiple items from a monday.com board using batch mutations.

    Parameters:
        api_key (str): The API key for authenticating with the monday.com API.
        item_ids (list): A list of item IDs to delete.

    Returns:
        list: A list of item IDs that were successfully deleted.
    """
    url = "https://api.monday.com/v2"
    headers = {
        "Authorization": api_key,
        "Content-Type": "application/json"
    }
    
    # Create a batch of delete mutations
    mutations = [
        {
            "query": """
            mutation {
                delete_item (item_id: %s) {
                    id
                }
            }
            """ % item_id
        }
        for item_id in item_ids
    ]
    
    try:
        async with aiohttp.ClientSession() as session:
            async with session.post(url, headers=headers, json=mutations) as response:
                response.raise_for_status()
                data = await response.json()
                if isinstance(data, list) and any("errors" in result for result in data):
                    raise MondayAPIError(f"API error: {data}")
                
                # Extract successful deletions
                successful_deletions = []
                for result in data:
                    if "data" in result and result["data"].get("delete_item", {}).get("id"):
                        successful_deletions.append(result["data"]["delete_item"]["id"])
                return successful_deletions
    except aiohttp.ClientError as e:
        raise MondayAPIError(f"Request failed: {e}")

@task
def delete_monday_items(api_key, item_ids, batch_size=50):
    """
    Delete all items from a monday.com board using batch mutations.

    Parameters:
        api_key (str): The API key for authenticating with the monday.com API.
        item_ids (list): A list of item IDs to delete.
        batch_size (int): Number of mutations to include in each batch.

    Returns:
        list: A list of item IDs that were successfully deleted.
    """
    successful_deletions = []
    for i in range(0, len(item_ids), batch_size):
        batch = item_ids[i:i + batch_size]
        try:
            deleted_ids = asyncio.run(delete_items_batch(api_key, batch))
            successful_deletions.extend(deleted_ids)
        except MondayAPIError as e:
            logger = get_run_logger()
            logger.error(f"Error deleting batch: {e}")
    return successful_deletions

@flow
def monday_flow(api_key, board_id):
    """
    Prefect flow to fetch all item IDs from a monday.com board and delete them in batches.
    """
    try:
        # Step 1: Fetch all item IDs
        item_ids = get_monday_item_ids(api_key, board_id)
        print(f"Fetched {len(item_ids)} item IDs: {item_ids}")

        # Step 2: Delete all items in batches
        deleted_item_ids = delete_monday_items(api_key, item_ids)
        print(f"Successfully deleted {len(deleted_item_ids)} item IDs: {deleted_item_ids}")
    except MondayAPIError as e:
        logger = get_run_logger()
        logger.error(f"Error in monday_flow: {e}")

# Run the flow
if __name__ == "__main__":
    api_key = "your_monday_api_key_here"
    board_id = "your_board_id_here"
    monday_flow(api_key, board_id)
