/*
 * Copyright (c) 2017 m2049r
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.m2049r.xmrwallet.fragment.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.m2049r.xmrwallet.R;
import com.m2049r.xmrwallet.data.PendingTx;
import com.m2049r.xmrwallet.data.TxData;
import com.m2049r.xmrwallet.util.Helper;

import timber.log.Timber;

public class SendSuccessWizardFragment extends SendWizardFragment {

    public static SendSuccessWizardFragment newInstance(Listener listener) {
        SendSuccessWizardFragment instance = new SendSuccessWizardFragment();
        instance.setSendListener(listener);
        return instance;
    }

    Listener sendListener;

    public SendSuccessWizardFragment setSendListener(Listener listener) {
        this.sendListener = listener;
        return this;
    }

    interface Listener {
        TxData getTxData();

        PendingTx getCommittedTx();

        void enableDone();

        SendFragment.Mode getMode();
    }

    ImageButton bCopyTxId;
    private TextView tvTxId;
    private TextView tvTxAddress;
    private TextView tvTxPaymentId;
    private TextView tvTxAmount;
    private TextView tvTxFee;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Timber.d("onCreateView() %s", (String.valueOf(savedInstanceState)));

        View view = inflater.inflate(
                R.layout.fragment_send_success, container, false);

        bCopyTxId = (ImageButton) view.findViewById(R.id.bCopyTxId);
        bCopyTxId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.clipBoardCopy(getActivity(), getString(R.string.label_send_txid), tvTxId.getText().toString());
                Toast.makeText(getActivity(), getString(R.string.message_copy_txid), Toast.LENGTH_SHORT).show();
            }
        });

        tvTxId = (TextView) view.findViewById(R.id.tvTxId);
        tvTxAddress = (TextView) view.findViewById(R.id.tvTxAddress);
        tvTxPaymentId = (TextView) view.findViewById(R.id.tvTxPaymentId);
        tvTxAmount = ((TextView) view.findViewById(R.id.tvTxAmount));
        tvTxFee = (TextView) view.findViewById(R.id.tvTxFee);

        return view;
    }

    @Override
    public boolean onValidateFields() {
        return true;
    }

    @Override
    public void onPauseFragment() {
        super.onPauseFragment();
    }

    @Override
    public void onResumeFragment() {
        super.onResumeFragment();
        Timber.d("onResumeFragment()");
        Helper.hideKeyboard(getActivity());

        final TxData txData = sendListener.getTxData();
        tvTxAddress.setText(txData.getDestinationAddress());
        String paymentId = txData.getPaymentId();
        if ((paymentId != null) && (!paymentId.isEmpty())) {
            tvTxPaymentId.setText(txData.getPaymentId());
        } else {
            tvTxPaymentId.setText("-");
        }

        final PendingTx committedTx = sendListener.getCommittedTx();
        if (committedTx != null) {
            tvTxId.setText(committedTx.txId);
            bCopyTxId.setEnabled(true);
            bCopyTxId.setImageResource(R.drawable.ic_content_copy_black_24dp);
            tvTxAmount.setText(getString(R.string.send_amount, Helper.getDisplayAmount(committedTx.amount)));
            tvTxFee.setText(getString(R.string.send_fee, Helper.getDisplayAmount(committedTx.fee)));
        }
        sendListener.enableDone();
    }
}
