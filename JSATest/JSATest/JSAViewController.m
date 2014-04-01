//
//  JSAViewController.m
//  JSATest
//
//  Created by Vesko Georgiev on 08/02/14.
//  Copyright (c) 2014 Vesko Georgiev. All rights reserved.
//

#import "JSAViewController.h"
#import "items.h"
#import "TTransport.h"
#import "THTTPClient.h"
#import "TBinaryProtocol.h"

@interface JSAViewController ()

@property ItemsAPIClient *client;

@end

@implementation JSAViewController

- (void)viewDidLoad
{
    [super viewDidLoad];

    NSURL *address = [[NSURL alloc] initWithString:@"http://localhost:8080/api/ItemsAPI/v1/thrift"];

    NSObject<TTransport> *clientTransport = [[THTTPClient alloc] initWithURL: address];
    NSObject<TProtocol> *protocol = [[TBinaryProtocol alloc] initWithTransport:clientTransport];

    self.client = [[ItemsAPIClient alloc] initWithProtocol:protocol];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)sendRequest:(id)sender
{
    @try {
        NSMutableArray *items = [self.client getItems];
        NSLog (@"%@", items);
    }
    @catch (NSException *e) {
        NSLog(@"%@", e);
 //       @throw e;
    }
}

@end
