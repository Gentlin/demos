//
//  MsgCellModel.h
//  demo
//
//  Created by lin on 2019/7/9.
//  Copyright © 2019 lin. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN
@protocol YYModel;

@interface MsgCellModel : NSObject  <NSCoding, YYModel>
@property (nonatomic, copy) NSString *msgId;
@property (nonatomic, copy) NSString *picture;
@property (nonatomic, copy) NSString *name;
@property (nonatomic, copy) NSString *message;
@property (nonatomic, copy) NSString *time;
@property (nonatomic, assign) NSInteger unreadCount;
@end

NS_ASSUME_NONNULL_END
