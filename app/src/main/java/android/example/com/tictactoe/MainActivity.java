package android.example.com.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int mPlayerChance;
    private TextView mStatus;
    private List<Pair<Integer, Integer>> mPlayerOneMoves;
    private List<Pair<Integer, Integer>> mPlayerTwoMoves;
    private boolean[][] mFilled;
    private boolean mOver;
    private int mWinner;
    private ImageButton[][] mCells;
    private WinnerDialog mWinnerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        resetGame(null);
    }

    private void setup() {
        mPlayerOneMoves = new ArrayList<> ();
        mPlayerTwoMoves = new ArrayList<> ();
        mFilled = new boolean[3][3];
        mCells = new ImageButton[3][3];
        mCells[0][0] = findViewById(R.id.btn00);
        mCells[0][1] = findViewById(R.id.btn01);
        mCells[0][2] = findViewById(R.id.btn02);
        mCells[1][0] = findViewById(R.id.btn10);
        mCells[1][1] = findViewById(R.id.btn11);
        mCells[1][2] = findViewById(R.id.btn12);
        mCells[2][0] = findViewById(R.id.btn20);
        mCells[2][1] = findViewById(R.id.btn21);
        mCells[2][2] = findViewById(R.id.btn22);
        mStatus = findViewById(R.id.status);
    }

    public void resetGame(View view) {
        try {
            mPlayerOneMoves.clear();
            mPlayerTwoMoves.clear();
        }
        catch(NullPointerException e){
            //pass
        }
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                mCells[i][j].setImageResource(0);
                mFilled[i][j] = false;
            }
        }
        mPlayerChance = 1;
        mStatus.setTextColor(getResources().getColor(R.color.colorBlack));
        mStatus.setText("Player " + mPlayerChance + " chance!");
        mOver = false;
        mWinner = 0;
    }

    public void onClick(View view) {
        String name = getResources().getResourceEntryName(view.getId());
        int i = Integer.parseInt(name.substring(3, 4));
        int j = Integer.parseInt(name.substring(4, 5));
        if(!mOver) {
            if (mFilled[i][j]) {
                Toast.makeText(getApplicationContext(), "Cell already filled up", Toast.LENGTH_SHORT)
                        .show();
            } else {
                mFilled[i][j] = true;
                if (mPlayerChance == 1) {
                    mPlayerOneMoves.add(Pair.create(i, j));
                    mCells[i][j].setImageResource(R.drawable.crcl_new);
                    //Toast.makeText(getApplicationContext(), mPlayerOneMoves.toString(), Toast.LENGTH_LONG).show();
                    if(didFirstPlayerWin()){
                        mOver = true;
                        mWinner = 1;
                    }
                    if(!mOver){
                        if(mPlayerTwoMoves.size() == 4)
                            mOver = true;
                    }
                } else {
                    mPlayerTwoMoves.add(Pair.create(i, j));
                    mCells[i][j].setImageResource(R.drawable.crss_new);
                    //Toast.makeText(getApplicationContext(), mPlayerTwoMoves.toString(), Toast.LENGTH_LONG).show();
                    if(didSecondPlayerWin()){
                        mOver = true;
                        mWinner = 2;
                    }
                }
                if(!mOver) {
                    mPlayerChance = 3 - mPlayerChance;
                    mStatus.setText("Player " + mPlayerChance + " chance!");
                }
                else{
                    if(mWinner == 0){
                        mWinnerDialog = new WinnerDialog("Match was tied!");
                        mStatus.setTextColor(getResources().getColor(R.color.colorRed));
                        mStatus.setText("Match Draw!");
                    }
                    else{
                        mWinnerDialog = new WinnerDialog("Player " + mWinner + " won!");
                        mStatus.setTextColor(getResources().getColor(R.color.colorGreen));
                        mStatus.setText("Player " + mWinner +" won!");
                    }

                    mWinnerDialog.showNow(getSupportFragmentManager(), "Hello");
                }
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Game Over! Please Restart", Toast.LENGTH_LONG).show();
        }
    }

    private boolean didFirstPlayerWin(){
        for(int i=0;i<mPlayerOneMoves.size();i++){

            int row1 = mPlayerOneMoves.get(i).first;
            int col1 = mPlayerOneMoves.get(i).second;

            for(int j=i+1;j<mPlayerOneMoves.size();j++){

                int row2 = mPlayerOneMoves.get(j).first;
                int col2 = mPlayerOneMoves.get(j).second;

                for(int k=j+1;k<mPlayerOneMoves.size();k++){

                    int row3 = mPlayerOneMoves.get(k).first;
                    int col3 = mPlayerOneMoves.get(k).second;

                    if(row1 == row2 && row2 == row3)
                        return true;
                    else if(col1 == col2 && col2 == col3)
                        return true;
                    else if(row1 == col1 && row2 == col2 && row3 == col3)
                        return true;
                    else if(row1+col1 == 2 && row2+col2 == 2 && row3+col3 == 2)
                        return true;
                }
            }
        }
        return false;
    }

    private boolean didSecondPlayerWin(){
        for(int i=0;i<mPlayerTwoMoves.size();i++){

            int row1 = mPlayerTwoMoves.get(i).first;
            int col1 = mPlayerTwoMoves.get(i).second;

            for(int j=i+1;j<mPlayerTwoMoves.size();j++){

                int row2 = mPlayerTwoMoves.get(j).first;
                int col2 = mPlayerTwoMoves.get(j).second;

                for(int k=j+1;k<mPlayerTwoMoves.size();k++){

                    int row3 = mPlayerTwoMoves.get(k).first;
                    int col3 = mPlayerTwoMoves.get(k).second;

                    if(row1 == row2 && row2 == row3)
                        return true;
                    else if(col1 == col2 && col2 == col3)
                        return true;
                    else if(row1 == col1 && row2 == col2 && row3 == col3)
                        return true;
                    else if(row1+col1 == 2 && row2+col2 == 2 && row3+col3 == 2)
                        return true;
                }
            }
        }
        return false;
    }
}
