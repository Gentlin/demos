//
//  NewcPureTitleCell.m
//  demo
//
//  Created by lin on 2019/7/7.
//  Copyright © 2019 lin. All rights reserved.
//

#import "NewsPureTitleCell.h"



@implementation NewsPureTitleCell
- (instancetype)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        self.title = [[UILabel alloc] init];
        [self.contentView addSubview:self.title];
        
        self.userName = [[UILabel alloc] init];
        [self.contentView addSubview:self.userName];
        
        self.commentCount= [[UILabel alloc] init];
        [self.contentView addSubview:self.commentCount];
        
        self.publishTime = [[UILabel alloc] init];
        [self.contentView addSubview:self.publishTime];
    }
    return self;
}

- (void)layoutSubviews {
    [super layoutSubviews];
    [self refreshUI];
}

- (void)refreshUI {
    NewsCellData *data = self.data;
    NSUInteger height = 10;
    self.title.frame = CGRectMake(10, height,  405, 60);
    self.title.text = data.title;
    UIFont *titleFont = [UIFont boldSystemFontOfSize:25.f];
    self.title.font = titleFont;
    self.title.textAlignment = NSTextAlignmentLeft;
    self.title.numberOfLines = 2;
    height = 70;
    
    UIFont *bottomFont = [UIFont systemFontOfSize:15.0f];
    int xOffset = 5;
    self.userName.frame = CGRectMake(xOffset, height,  0, 0);
    self.userName.text = data.userName;
    self.userName.font = bottomFont;
    [self.userName sizeToFit];
    xOffset += [self.userName.text sizeWithAttributes:[NSDictionary dictionaryWithObjectsAndKeys:bottomFont,NSFontAttributeName,nil]].width;
    xOffset += 10;
    
    self.commentCount.frame = CGRectMake(xOffset, height,  0,0);
    self.commentCount.text = [NSString stringWithFormat:@"%lu评论",data.commentCount];
    
    self.commentCount.font = bottomFont;
    [self.commentCount sizeToFit];
    xOffset += [self.commentCount.text sizeWithAttributes:[NSDictionary dictionaryWithObjectsAndKeys:bottomFont,NSFontAttributeName,nil]].width;
    xOffset += 10;
    self.publishTime.frame = CGRectMake(xOffset, height,  0, 0);
    NSDateFormatter *formatter = [[NSDateFormatter alloc] init];
    [formatter setDateFormat:@"yyyy-MM-dd"];
    self.publishTime.text = [formatter stringFromDate:[NSDate dateWithTimeIntervalSince1970:(double)[data.publishTime intValue]]];
    self.publishTime.font = bottomFont;
    [self.publishTime sizeToFit];
    xOffset += [self.publishTime.text sizeWithAttributes:[NSDictionary dictionaryWithObjectsAndKeys:bottomFont,NSFontAttributeName,nil]].width;
    // self.height = height + 20;
}

@end
