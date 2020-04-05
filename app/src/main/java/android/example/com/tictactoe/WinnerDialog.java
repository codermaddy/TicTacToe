package android.example.com.tictactoe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class WinnerDialog extends DialogFragment {

    private String mResultMessage;

    public WinnerDialog(String msg){
        mResultMessage = msg;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(mResultMessage)
                .setTitle("Game Over!")
               .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       //ok
                   }
               });
        return builder.create();
    }
}
