package com.ahmedelouha.telfaza.matchdetail;

/**
 * Created by raaja on 30-12-2017.
 */

public interface GoalWebContract {

    interface View{
        void loadGoalPage();
        void setPresenter(GoalWebContract.Presenter presenter);
        void showLoader();
        void hideLoader();
    }

    interface Presenter{
        void openGoalPage();
    }
}
