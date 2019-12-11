//
//  NewsListController.m
//  demo
//
//  Created by lin on 2019/7/7.
//  Copyright © 2019 lin. All rights reserved.
//

#import "NewsListController.h"
#import "NewsPicCell.h"
#import "NewsPureTitleCell.h"
#import "NewsDataManager.h"
#import "DetailViewController.h"

@interface  NewsListController ()

@property (nonatomic, strong) UITableView *newsTableView;
@property (nonatomic, copy) NSArray *newsData;
@end

@implementation NewsListController 

- (id)init {
    if (self = [super init]) {
        _newsTableView = [[UITableView alloc] initWithFrame:[UIScreen mainScreen].bounds];
        _newsData = nil;
        [self.view addSubview:_newsTableView];
    }
    return self;
}

- (void)viewDidLoad {
    _newsTableView.delegate = self;
    _newsTableView.dataSource = self;
    [NewsDataManager fetchNewsData:^(NSArray *newsData, NSError *error) {
        if (error) {
           NSLog(@"%@", error);
           return ;
        }
        if (newsData) {
            self.newsData = newsData;
            [self.newsTableView reloadData];
        }
    }];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.newsData.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    NSString *cellIdentifier;
    NewsCellData *cellData = self.newsData[indexPath.row];
    if (cellData.images != nil) {
        cellIdentifier = @"NewsPicCell";
    } else {
        cellIdentifier = @"NewsPureTitleCell";
    }
    NewsCell *cell = [tableView dequeueReusableCellWithIdentifier:cellIdentifier];
    if (!cell) {
        cell = [[[NSClassFromString(cellIdentifier) alloc] init] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:cellIdentifier];
    }
    cell.data = cellData;
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    NewsCellData *d = self.newsData[indexPath.row];
    if (d.images == nil)return 90;
    return 220;
}

- (UIStatusBarStyle)preferredStatusBarStyle {
    return UIStatusBarStyleLightContent;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    NewsCellData *newsItem = self.newsData[indexPath.row];
    NSString *url = newsItem.articleUrl;
    if (url != nil) {
        DetailViewController *detailViewController = [[DetailViewController alloc] initWithUrl:url];
        UIWindow *window = [[UIApplication sharedApplication] keyWindow];
        UINavigationController *navigationController = (UINavigationController *)window.rootViewController;
        [navigationController pushViewController:detailViewController animated:NO];
    }
}
@end
