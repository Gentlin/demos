//
//  NewsCell.h
//  demo
//
//  Created by lin on 2018/12/7.
//  Copyright © 2018年 lin. All rights reserved.
//
#import <UIKit/UIKit.h>
#import "NewsCellData.h"

#ifndef NewsCell_h
#define NewsCell_h

@interface NewsCell : UITableViewCell
@property (nonatomic, weak) NewsCellData *data;

- (void)refreshUI;
@end
#endif /* NewsCell_h */
