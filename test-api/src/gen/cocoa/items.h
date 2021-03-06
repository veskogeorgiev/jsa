/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */

#import <Foundation/Foundation.h>

#import "TProtocol.h"
#import "TApplicationException.h"
#import "TProtocolException.h"
#import "TProtocolUtil.h"
#import "TProcessor.h"
#import "TObjective-C.h"
#import "TBase.h"


@interface IDGItem : NSObject <TBase, NSCoding> {
  NSString * __id;
  NSString * __name;
  int32_t __count;

  BOOL __id_isset;
  BOOL __name_isset;
  BOOL __count_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, retain, getter=id, setter=setId:) NSString * id;
@property (nonatomic, retain, getter=name, setter=setName:) NSString * name;
@property (nonatomic, getter=count, setter=setCount:) int32_t count;
#endif

- (id) init;
- (id) initWithId: (NSString *) id name: (NSString *) name count: (int32_t) count;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (void) validate;

#if !__has_feature(objc_arc)
- (NSString *) id;
- (void) setId: (NSString *) id;
#endif
- (BOOL) idIsSet;

#if !__has_feature(objc_arc)
- (NSString *) name;
- (void) setName: (NSString *) name;
#endif
- (BOOL) nameIsSet;

#if !__has_feature(objc_arc)
- (int32_t) count;
- (void) setCount: (int32_t) count;
#endif
- (BOOL) countIsSet;

@end

@interface IDGItemResult : NSObject <TBase, NSCoding> {
  NSString * __name;
  NSMutableArray * __items;

  BOOL __name_isset;
  BOOL __items_isset;
}

#if TARGET_OS_IPHONE || (MAC_OS_X_VERSION_MAX_ALLOWED >= MAC_OS_X_VERSION_10_5)
@property (nonatomic, retain, getter=name, setter=setName:) NSString * name;
@property (nonatomic, retain, getter=items, setter=setItems:) NSMutableArray * items;
#endif

- (id) init;
- (id) initWithName: (NSString *) name items: (NSMutableArray *) items;

- (void) read: (id <TProtocol>) inProtocol;
- (void) write: (id <TProtocol>) outProtocol;

- (void) validate;

#if !__has_feature(objc_arc)
- (NSString *) name;
- (void) setName: (NSString *) name;
#endif
- (BOOL) nameIsSet;

#if !__has_feature(objc_arc)
- (NSMutableArray *) items;
- (void) setItems: (NSMutableArray *) items;
#endif
- (BOOL) itemsIsSet;

@end

@protocol IDGItemsAPI <NSObject>
- (NSMutableArray *) getItems;  // throws TException
- (IDGItemResult *) getItemResult;  // throws TException
- (NSMutableDictionary *) getMapResult;  // throws TException
- (void) save: (IDGItem *) item;  // throws TException
@end

@interface IDGItemsAPIClient : NSObject <IDGItemsAPI> {
  id <TProtocol> inProtocol;
  id <TProtocol> outProtocol;
}
- (id) initWithProtocol: (id <TProtocol>) protocol;
- (id) initWithInProtocol: (id <TProtocol>) inProtocol outProtocol: (id <TProtocol>) outProtocol;
@end

@interface IDGItemsAPIProcessor : NSObject <TProcessor> {
  id <IDGItemsAPI> mService;
  NSDictionary * mMethodMap;
}
- (id) initWithItemsAPI: (id <IDGItemsAPI>) service;
- (id<IDGItemsAPI>) service;
@end

@interface IDGitemsConstants : NSObject {
}
@end
