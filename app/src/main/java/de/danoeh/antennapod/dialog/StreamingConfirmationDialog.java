package de.danoeh.antennapod.dialog;

import android.content.Context;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import de.danoeh.antennapod.R;
import de.danoeh.antennapod.core.preferences.UserPreferences;
import de.danoeh.antennapod.model.playback.Playable;
import de.danoeh.antennapod.core.util.playback.PlaybackServiceStarter;

public class StreamingConfirmationDialog {
    private final Context context;
    private final Playable playable;

    public StreamingConfirmationDialog(Context context, Playable playable) {
        this.context = context;
        this.playable = playable;
    }

    public void show() {
        new MaterialAlertDialogBuilder(context)
                .setTitle(R.string.stream_label)
                .setMessage(R.string.confirm_mobile_streaming_notification_message)
                .setPositiveButton(R.string.confirm_mobile_streaming_button_once, (dialog, which) -> stream())
                .setNegativeButton(R.string.confirm_mobile_streaming_button_always, (dialog, which) -> {
                    UserPreferences.setAllowMobileStreaming(true);
                    stream();
                })
                .setNeutralButton(R.string.cancel_label, null)
                .show();
    }

    private void stream() {
        new PlaybackServiceStarter(context, playable)
                .callEvenIfRunning(true)
                .shouldStreamThisTime(true)
                .start();
    }
}
