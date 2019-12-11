//
//  MsgCell.h
//  demo
//
//  Created by lin on 2019/7/9.
//  Copyright © 2019 lin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
NS_ASSUME_NONNULL_BEGIN
@class MsgCellModel;
@interface MsgCell : UITableViewCell
@property (nonatomic, weak) MsgCellModel *model;
@property (nonatomic, assign) NSInteger height;
@end

NS_ASSUME_NONNULL_END
