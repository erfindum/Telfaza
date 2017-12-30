package com.ahmedelouha.telfaza.matchdetail;

/**
 * Created by raaja on 30-12-2017.
 */

public class GoalWebPresenter implements GoalWebContract.Presenter {

    GoalWebContract.View view;

    GoalWebPresenter(GoalWebContract.View view){
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void openGoalPage() {
        view.showLoader();
        view.loadGoalPage();
    }
}
