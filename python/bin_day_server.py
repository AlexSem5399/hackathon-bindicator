import socket
import requests
from bs4 import BeautifulSoup

from datetime import datetime

def send_to_bin(collection_type):
  try:
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
  
    sock.connect(('10.0.1.1', 9999))

    sock.send(collection_type.encode('utf-8'))
  
    sock.close()
  except:
    print("Problem connecting and/or sending data.")
    
def parse_response(collection_type, collection_date):
    current_date = datetime.now().date().strftime("%d %B %Y")
    if current_date in collection_date:
      if 'Recycling' in collection_type:
        send_to_bin("R") # R for recycling
      elif 'Refuse' in collection_type:
        send_to_bin("T") # T for trash
    else:
      pass

def request_bin_day():
    URL = "https://www.runnymede.gov.uk/bin-collection-day?address=100061498425"
    response = requests.get(URL)

    soup = BeautifulSoup(response.content, "html.parser")

    match response.status_code: 
      case 200:
        all_table_elements = soup.find_all("td")
        
        # <td><span class="icon icon-bins-recycling"></span>Recycling</td>
        collection_type = str(all_table_elements[0]) # next type of collection
        # <td>Monday, 29 January 2024</td>
        collection_date = str(all_table_elements[1]) # next date of collection

        # FOR TESTING PURPOSES
        collection_type = '<td><span class="icon icon-bins-recycling"></span>Recycling</td>'
        collection_date = "<td>Sunday, 28 January 2024</td>"
        

        parse_response(collection_type, collection_date)
      case _:
        print("Error code received")
            
if __name__ == '__main__':
    request_bin_day()