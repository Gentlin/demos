//
//  AppDelegate.m
//  secretary
//
//  Created by linjiantao on 2019/12/8.
//  Copyright © 2019 linjiantao. All rights reserved.
//

#import "AppDelegate.h"
#import "ViewController.h"
#import "MsgListController.h"
#import "NewsListController.h"

@interface AppDelegate ()

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    // Override point for customization after application launch.
    UITabBarController *tabBarController = [[UITabBarController alloc] init];
    self.window.rootViewController = tabBarController;
    ViewController *vc = [[ViewController alloc] init];
    vc.tabBarItem.title = @"其它";
    MsgListController *msgListVC = [[MsgListController alloc] init];
    msgListVC.tabBarItem.title = @"消息";
    NewsListController *newsListVC = [[NewsListController alloc] init];
    newsListVC.tabBarItem.title = @"新闻";
    tabBarController.viewControllers = @[msgListVC, newsListVC, vc];
    [self.window makeKeyAndVisible];
    return YES;
}


#pragma mark - UISceneSession lifecycle


- (UISceneConfiguration *)application:(UIApplication *)application configurationForConnectingSceneSession:(UISceneSession *)connectingSceneSession options:(UISceneConnectionOptions *)options {
    // Called when a new scene session is being created.
    // Use this method to select a configuration to create the new scene with.
    return [[UISceneConfiguration alloc] initWithName:@"Default Configuration" sessionRole:connectingSceneSession.role];
}


- (void)application:(UIApplication *)application didDiscardSceneSessions:(NSSet<UISceneSession *> *)sceneSessions {
    // Called when the user discards a scene session.
    // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
    // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
}


@end
