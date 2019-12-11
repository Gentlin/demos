//
//  NewsCellData.h
//  demo
//
//  Created by lin on 2018/12/7.
//  Copyright © 2018年 lin. All rights reserved.
//
#import <Foundation/Foundation.h>
#ifndef NewsCellData_h
#define NewsCellData_h
@interface NewsCellData : NSObject

@property (nonatomic, copy)  NSString *title;
@property (nonatomic, copy)  NSString *userName; // TODO: copy strong
@property (nonatomic, assign) NSUInteger commentCount;
@property (nonatomic, copy) NSNumber *publishTime; // TODO: convert timestamp to nsdate
@property (nonatomic, copy) NSArray *images;
@property (nonatomic, copy) NSString *articleUrl;

- (NewsCellData *) initWithTitle:(NSString *)title userName:(NSString *)userName commentCount:(NSUInteger)commentCount publishTime:(NSNumber *)publishTime imagesUrls:(NSArray *)imageUrls articleUrl:(NSString *)url;

@end
#endif /* NewsCellData_h */
