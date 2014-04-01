////////////////////////////////////////////////////////////
// ItemsAPI version: v2:2
////////////////////////////////////////////////////////////

namespace java jsa.test.api.v2.thrift
namespace cocoa v2

struct Item {
  1: string description
  2: string name
  3: i32 count
}

service ItemsAPI {
  void saveItem(1: string arg1, 2: i32 arg2, 3: string arg3)
  list<Item> availableItems()
}
