package com.maxfour.music.preferences;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.maxfour.music.R;
import com.maxfour.music.adapter.CategoryInfoAdapter;
import com.maxfour.music.model.CategoryInfo;
import com.maxfour.music.util.PreferenceUtil;

import java.util.ArrayList;


public class LibraryPreferenceDialog extends DialogFragment {
    public static LibraryPreferenceDialog newInstance() {
        return new LibraryPreferenceDialog();
    }

    private CategoryInfoAdapter adapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.preference_dialog_library_categories, null);

        ArrayList<CategoryInfo> categoryInfos;
        if (savedInstanceState != null) {
            categoryInfos = savedInstanceState.getParcelableArrayList(PreferenceUtil.LIBRARY_CATEGORIES);
        } else {
            categoryInfos = PreferenceUtil.getInstance(getContext()).getLibraryCategoryInfos();
        }
        adapter = new CategoryInfoAdapter(categoryInfos);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.attachToRecyclerView(recyclerView);

        return new MaterialDialog.Builder(getContext())
                .title(R.string.library_categories)
                .customView(view, false)
                .positiveText(android.R.string.ok)
                .negativeText(android.R.string.cancel)
                .neutralText(R.string.reset_action)
                .autoDismiss(false)
                .onNeutral((dialog, action) -> adapter.setCategoryInfos(PreferenceUtil.getInstance(getContext()).getDefaultLibraryCategoryInfos()))
                .onNegative((dialog, action) -> dismiss())
                .onPositive((dialog, action) -> {
                    updateCategories(adapter.getCategoryInfos());
                    dismiss();
                })
                .build();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PreferenceUtil.LIBRARY_CATEGORIES, adapter.getCategoryInfos());
    }

    private void updateCategories(ArrayList<CategoryInfo> categories) {
        if (getSelected(categories) == 0) return;

        PreferenceUtil.getInstance(getContext()).setLibraryCategoryInfos(categories);
    }

    private int getSelected(ArrayList<CategoryInfo> categories) {
        int selected = 0;
        for (CategoryInfo categoryInfo : categories) {
            if (categoryInfo.visible)
                selected++;
        }
        return selected;
    }
}
