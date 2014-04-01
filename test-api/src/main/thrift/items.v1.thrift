////////////////////////////////////////////////////////////
// ItemsAPI version: v1:1
////////////////////////////////////////////////////////////

namespace java jsa.test.api.v1
namespace cocoa v1

struct Item {
  1: string name
  2: i32 count
}

service ItemsAPI {
  list<Item> availableItems()
  void saveItem(1: string arg1, 2: i32 arg2)
}
