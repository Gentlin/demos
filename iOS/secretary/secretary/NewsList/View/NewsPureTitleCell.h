//
//  NewcPureTitleCell.h
//  demo
//
//  Created by lin on 2019/7/7.
//  Copyright © 2019 lin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "NewsCell.h"

NS_ASSUME_NONNULL_BEGIN

@interface NewsPureTitleCell : NewsCell
@property (nonatomic, strong) UILabel *title;
@property (nonatomic, strong) UILabel *userName;
@property (nonatomic, strong) UILabel *commentCount;
@property (nonatomic, strong) UILabel *publishTime;
@end

NS_ASSUME_NONNULL_END
