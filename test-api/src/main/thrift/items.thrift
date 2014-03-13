namespace java jsa.test.api.items.thrift
namespace cocoa IDG

struct Item {
  1: string id
  2: string name
  3: i32 count
}

struct ItemResult {
  1: string name
  2: list<Item> items
}

service ItemsAPI {
  list<Item> getItems()
  
  ItemResult getItemResult()

  map<string, Item> getMapResult()

  void save(Item item)
}
