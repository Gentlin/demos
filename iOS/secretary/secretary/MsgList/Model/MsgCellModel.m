//
//  MsgCellModel.m
//  demo
//
//  Created by lin on 2019/7/9.
//  Copyright © 2019 lin. All rights reserved.
//

#import "MsgCellModel.h"
#import <YYModel/YYModel.h>
@implementation MsgCellModel

- (void)encodeWithCoder:(NSCoder *)aCoder {
    [aCoder encodeObject:self.msgId forKey:@"_id"];
    [aCoder encodeObject:self.name forKey:@"name"];
    [aCoder encodeObject:self.message forKey:@"message"];
    [aCoder encodeObject:self.picture forKey:@"picture"];
    [aCoder encodeInteger:self.unreadCount forKey:@"unreadCount"];
    [aCoder encodeObject:self.time forKey:@"time"];
}

- (nullable instancetype)initWithCoder:(NSCoder *)aDecoder {
    self = [super init];
    if (self) {
        self.msgId = [aDecoder decodeObjectForKey:@"_id"];
        self.name = [aDecoder decodeObjectForKey:@"name"];
        self.message = [aDecoder decodeObjectForKey:@"message"];
        self.picture = [aDecoder decodeObjectForKey:@"picture"];
        self.unreadCount = [(NSNumber *)[aDecoder decodeObjectForKey:@"unreadCount"] longValue];
        self.time = [aDecoder decodeObjectForKey:@"time"];
    }
    return self;
}

+ (NSDictionary *)modelCustomPropertyMapper {
    return @{@"msgId" : @"_id",
             @"name" : @"name",
             @"message" : @"message",                 //key.path
             @"picture" : @"picture",
             @"unreadCount" : @"unreadCount",
             @"time" : @"time"
             };
   
}
@end
