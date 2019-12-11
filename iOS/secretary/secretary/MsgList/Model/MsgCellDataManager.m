//
//  MsgCellDataManager.m
//  demo
//
//  Created by lin on 2019/7/9.
//  Copyright © 2019 lin. All rights reserved.
//

#import "MsgCellDataManager.h"
#import "NewsDataManager.h"
#import "MsgCellModel.h"
#import <YYModel/YYModel.h>

@implementation MsgCellDataManager

+ (NSArray *)fetchMsgData {
    NSString *path = [[NSBundle mainBundle] pathForResource:@"sessions" ofType:@"json"];
    NSData *data = [NSData dataWithContentsOfFile:path];
    NSArray *msgs = [NSArray yy_modelArrayWithClass:[MsgCellModel class] json:data];
    
//    NSLog(@"%@", msgs);
    return msgs;
}
@end
