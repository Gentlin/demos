//
//  NewsDataManager.h
//  demo
//
//  Created by lin on 2019/7/7.
//  Copyright © 2019 lin. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "NewsCellData.h"
NS_ASSUME_NONNULL_BEGIN

@interface NewsDataManager : NSObject
+ (void)fetchNewsData:(void(^)(NSArray *newsData, NSError *error))completion;
@end

NS_ASSUME_NONNULL_END
