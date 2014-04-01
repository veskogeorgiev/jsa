////////////////////////////////////////////////////////////
// ItemsAPI version: v3:3
////////////////////////////////////////////////////////////

namespace java jsa.test.api.v3.thrift
namespace cocoa v3

struct Item {
  1: string name
  2: i32 count
  3: required string description
}

service ItemsAPI {
  list<Item> availableItems()
  void saveItem(1: string arg1, 2: i32 arg2, 3: required string arg3)
}
