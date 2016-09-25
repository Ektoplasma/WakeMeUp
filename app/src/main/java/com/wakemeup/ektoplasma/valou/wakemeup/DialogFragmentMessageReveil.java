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

        //String nomutilisateur =;
        //String message = ;

        //reveilAlert.setTitle("Reveil proposé par " + nomutilisateur);
        reveilAlert.setTitle("Wake Up!");

        LayoutInflater inflater = getActivity().getLayoutInflater();

        reveilAlert.setView(inflater.inflate(R.layout.send_message, null))

                .setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setMessage("Bonjour");
                //.setMessage(message);

        return reveilAlert.create();

    }
    public static DialogFragmentMessage newInstance() {
        DialogFragmentMessage f = new DialogFragmentMessage();
        return f;
    }
}
