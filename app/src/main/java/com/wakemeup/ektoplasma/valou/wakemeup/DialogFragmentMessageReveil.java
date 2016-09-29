package com.wakemeup.ektoplasma.valou.wakemeup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;

/**
 * Created by Valentin on 25/09/2016.
 */

public class DialogFragmentMessageReveil extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder reveilAlert = new AlertDialog.Builder(getActivity());

        String message = Caller.getCurrentMessage();
        final String voteur = Caller.getCurrentVoter();

        reveilAlert.setTitle("Reveil proposé par " +voteur+" !");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        //TODO : POUR ENVOYER REPONSE : Caller.sendMessage(reponse, voteur);
        reveilAlert.setView(inflater.inflate(R.layout.send_message, null))

                .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setMessage(message);

        return reveilAlert.create();

    }
    public static DialogFragmentMessageReveil newInstance() {
        DialogFragmentMessageReveil f = new DialogFragmentMessageReveil();
        return f;
    }
}
