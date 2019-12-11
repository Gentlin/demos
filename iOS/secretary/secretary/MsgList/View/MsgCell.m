//
//  MsgCell.m
//  demo
//
//  Created by lin on 2019/7/9.
//  Copyright © 2019 lin. All rights reserved.
//

#import "MsgCell.h"
#import "MsgCellModel.h"
#import "Masonry.h"

@interface MsgCell ()
@property (nonatomic, strong) UIImageView *picture;
@property (nonatomic, strong) UILabel *name;
@property (nonatomic, strong) UILabel *time;
@property (nonatomic, strong) UIView *redPoint;
@property (nonatomic, strong) UILabel *msg;
@end

@implementation MsgCell

- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier {
    if (self = [super initWithStyle:style reuseIdentifier:reuseIdentifier]) {
        _picture = [[UIImageView alloc] init];
        [self.contentView addSubview:_picture];
        
        _name = [[UILabel alloc] init];
        [self.contentView addSubview:_name];
        
        _time = [[UILabel alloc] init];
        [self.contentView addSubview:_time];
        _redPoint = ({
            UIView *view = [[UIView alloc] init];
            view.backgroundColor = [UIColor redColor];
            view.layer.cornerRadius = 5.f;
            view.layer.masksToBounds = YES;
            view.hidden = YES;
            view;
        });
        _msg = [[UILabel alloc] init];
        [self.contentView addSubview:_msg];
    }
    return self;
}

- (void)layoutSubviews {
    [super layoutSubviews];
    [self refreshUI];
}

- (void)setModel:(MsgCellModel *)model {
    _model = model;
    NSData *picData = [NSData dataWithContentsOfURL:[NSURL URLWithString:self.model.picture]];
    self.picture.image = [UIImage imageWithData:picData];
    self.name.text = model.name;
    self.msg.text = model.message;
    self.time.text = model.time;
    [self refreshUI];
}

- (void)refreshUI {
    if (!self.model)return;
    [self.picture mas_makeConstraints:^(MASConstraintMaker *make){
        make.top.equalTo(self.contentView).offset(10);
        make.bottom.offset(-10);
        make.left.offset(30);
        make.width.equalTo(self.picture.mas_height);
    }];
    [self.name mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.picture);
        make.left.equalTo(self.picture.mas_right).offset(20);
    }];
    [self.msg mas_makeConstraints:^(MASConstraintMaker *make) {
        make.top.equalTo(self.name.mas_bottom).offset(10);
        make.left.equalTo(self.name);
        make.right.equalTo(self.name);
    }];
    
}

- (NSInteger)height {
    return 90;
}
@end
