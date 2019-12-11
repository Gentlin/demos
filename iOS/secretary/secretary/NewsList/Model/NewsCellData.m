//
//  NewsCellData.m
//  demo
//
//  Created by lin on 2018/12/7.
//  Copyright © 2018年 lin. All rights reserved.
//

#import "NewsCellData.h"



@implementation NewsCellData : NSObject

- (NewsCellData *)initWithTitle:(NSString *)title userName:(NSString *)userName commentCount:(NSUInteger)commentCount publishTime:(NSNumber *)publishTime imagesUrls:(NSArray *)imageUrls articleUrl:(NSString *)url{
    if(self = [super init]) {
        self.title = title;
        self.userName = userName;
        self.commentCount = commentCount;
        self.publishTime = publishTime;
        self.images = nil;
        self.articleUrl = url;
        self.images =  imageUrls;
       // NSDate *date = [NSDate date];
    }
    return self;
}

@end
