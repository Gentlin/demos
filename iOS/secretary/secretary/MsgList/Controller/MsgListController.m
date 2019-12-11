//
//  MsgListController.m
//  demo
//
//  Created by lin on 2019/7/9.
//  Copyright © 2019 lin. All rights reserved.
//

#import "MsgListController.h"
#import "MsgCellDataManager.h"
#import "MsgCell.h"
#import "MsgCellModel.h"

@interface MsgListController ()
@property (nonatomic, strong) UITableView *tableView;
@property (nonatomic, copy) NSArray *msgData;
@end

@implementation MsgListController

- (id)init {
    if (self = [super init]) {
        _tableView = [[UITableView alloc] initWithFrame:[UIScreen mainScreen].bounds];
        _tableView.delegate = self;
        _tableView.dataSource = self;
        [self.view addSubview:_tableView];
    }
    return self;
}

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
    self.msgData = [MsgCellDataManager fetchMsgData];
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return 1;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    MsgCellModel *cellData = self.msgData[indexPath.row];
    MsgCell *cell = [self.tableView dequeueReusableCellWithIdentifier:@"MsgCell"];
    if (!cell) {
        cell = [[MsgCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:@"MsgCell"];
    }
    cell.model = cellData;
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath {
    MsgCell *cell = [self.tableView dequeueReusableCellWithIdentifier:@"MsgCell"];
    if (!cell) {
        cell = [[MsgCell alloc] init];
    }
    return cell.height;
}
@end
