//
//  NewsPicCell.h
//  demo
//
//  Created by lin on 2019/7/7.
//  Copyright © 2019 lin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "NewsCell.h"

NS_ASSUME_NONNULL_BEGIN

@interface NewsPicCell : NewsCell

@property (nonatomic, strong) UILabel *title;
@property (nonatomic, strong) UILabel *userName;
@property (nonatomic, strong) UILabel *commentCount;
@property (nonatomic, strong) UILabel *publishTime;
@property (nonatomic, strong) NSMutableArray *images;

@end

NS_ASSUME_NONNULL_END
