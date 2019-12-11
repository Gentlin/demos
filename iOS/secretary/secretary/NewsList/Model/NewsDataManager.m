//
//  NewsDataManager.m
//  demo
//
//  Created by lin on 2019/7/7.
//  Copyright © 2019 lin. All rights reserved.
//

#import "NewsDataManager.h"

@implementation NewsDataManager
+ (void)fetchNewsData:(void(^)(NSArray *newsData, NSError *error))completion {
    NSURL *url = [NSURL URLWithString:@"https://is.snssdk.com/api/news/feed/v88/?fp=c2TqLMKbFYGWFlGuF2U1FlxScrwS&version_code=6.8.7&app_name=news_article&vid=B3A90F95-D558-4703-B266-B7FCB74EDA57&device_id=10360556888&resolution=1125*2436&aid=13"];
    NSURLSession *session = [NSURLSession sharedSession];
    [[session dataTaskWithURL:url
                 completionHandler:^(NSData *data,
                                     NSURLResponse *response,
                                     NSError *error) {
                     NSMutableArray *newsData = [[NSMutableArray alloc] init];
                     NSDictionary *json = [NSJSONSerialization JSONObjectWithData:data options:NSJSONReadingMutableLeaves | NSJSONReadingMutableContainers error:&error];
                     NSArray *list = json[@"data"];
                     if (list.count > 0) {
                         dispatch_async(dispatch_get_main_queue(), ^{
                             for(NSDictionary* ns in list) {
                                 NSString *content = [ns objectForKey:@"content"];
                                 NSDictionary *contentDict = [NSJSONSerialization JSONObjectWithData:[content dataUsingEncoding:NSUTF16StringEncoding] options:0 error:nil];
                                 if (contentDict[@"allow_download"] == false)continue;
                                 
//                                 NSLog(@"%@ %@ %@ %@ %@",
//                                       [contentDict valueForKey:@"title"],
//                                       [contentDict valueForKeyPath:@"user_info.name"],
//                                       [contentDict valueForKey:@"comment_count"],
//                                       [contentDict valueForKey:@"publish_time"],
//                                       [contentDict valueForKey:@"article_url"]);
                                 NSUInteger commentCount = [[contentDict valueForKey:@"comment_count"] unsignedIntegerValue];
                          
                                 NSArray *images = [contentDict valueForKeyPath:@"image_list"];
                                 NSArray *imageUrls;
                                 if(images != nil) {
                                    imageUrls = [[NSArray alloc] initWithObjects:
                                                                images[0][@"url_list"][0][@"url"],
                                                                [contentDict valueForKey:@"middle_image.url_list"][0][@"url_list"][0][@"url"],
                                                                [contentDict valueForKey:@"large_image.url_list"][0][@"url_list"][0][@"url"],
                                                                nil];
                                 //    NSLog(@"---images%@", imageUrls);
                                 }
                                 
                                 NewsCellData *newsCellDataItem = [[NewsCellData alloc]
                                                                   initWithTitle:[contentDict valueForKey:@"title"]
                                                                   userName:[contentDict valueForKeyPath:@"user_info.name"]
                                                                   commentCount:commentCount
                                                                   publishTime:[contentDict valueForKey:@"publish_time"]
                                                                   imagesUrls:imageUrls
                                                                   articleUrl:[contentDict valueForKey:@"article_url"]];
                                 [newsData addObject:newsCellDataItem];
                             }
                             if (completion) {
                                 completion(newsData, error);
                             }
                         });
                     }
                 }] resume];
}
@end
