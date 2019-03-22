package com.bw.movie.activity.seat;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bw.movie.R;
import com.bw.movie.mvp.MVPBaseActivity;
import com.bw.movie.seat.SeatTable;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SeatActivity extends MVPBaseActivity<SeatContract.View, SeatPresenter> implements SeatContract.View {
    public SeatTable seatTableView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        seatTableView = (SeatTable) findViewById(R.id.seatView);
        seatTableView.setScreenName("8号厅荧幕");//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if(column==2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if(row==6&&column==6){
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10,15);

    }
}
