////////////////////////////////////////////////////////////
// ItemsAPI version: v3:3
////////////////////////////////////////////////////////////

namespace java jsa.test.api.v3
namespace cocoa v3

struct Item {
  1: string description
  2: string name
  3: i32 count
}

service ItemsAPI {
  list<Item> availableItems()
  void saveItem(1: string arg1, 2: i32 arg2, 3: string arg3)
}
