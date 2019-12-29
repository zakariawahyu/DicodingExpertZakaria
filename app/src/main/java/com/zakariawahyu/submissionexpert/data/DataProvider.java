package com.zakariawahyu.submissionexpert.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zakariawahyu.submissionexpert.fragment.FilmFavFragment;
import com.zakariawahyu.submissionexpert.fragment.TvShowFavFragment;

import java.util.Objects;

import static com.zakariawahyu.submissionexpert.data.DataContract.AUTHORITY;

public class DataProvider extends ContentProvider {

    private static final int FILM = 1;
    private static final int FILM_ID = 2;
    private static final int TV_SHOW = 3;
    private static final int TV_SHOW_ID = 4;
    private static final UriMatcher stringUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private FilmHelper filmHelper;
    private TvShowsHelper tvShowsHelper;

    static {
        stringUriMatcher.addURI(AUTHORITY, DataContract.FilmFavEntry.TABLE_FILM, FILM);
        stringUriMatcher.addURI(AUTHORITY, DataContract.FilmFavEntry.TABLE_FILM + "/#", FILM_ID);

        stringUriMatcher.addURI(AUTHORITY, DataContract.TvShowsFavEntry.TABLE_TVSHOWS, TV_SHOW);
        stringUriMatcher.addURI(AUTHORITY, DataContract.TvShowsFavEntry.TABLE_TVSHOWS + "/#", TV_SHOW_ID);
    }

    @Override
    public boolean onCreate() {
        filmHelper = FilmHelper.getInstance(getContext());
        tvShowsHelper = TvShowsHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        filmHelper.open();
        tvShowsHelper.open();
        Cursor cursor;
        switch (stringUriMatcher.match(uri)) {
            case FILM:
                cursor = filmHelper.queryProviderFilm();
                break;
            case FILM_ID:
                cursor = filmHelper.queryByIdProviderFilm(uri.getLastPathSegment());
                break;
            case TV_SHOW:
                cursor = tvShowsHelper.queryProviderTvShows();
                break;
            case TV_SHOW_ID:
                cursor = tvShowsHelper.queryByIdProviderTvShows(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        filmHelper.open();
        tvShowsHelper.open();
        long added;
        Uri favoriteUri;
        switch (stringUriMatcher.match(uri)) {
            case FILM:
                added = filmHelper.insertProviderFilm(values);
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DataContract.FilmFavEntry.CONTENT_URI, new FilmFavFragment.DataObserver(new Handler(), getContext()));
                favoriteUri = Uri.parse(DataContract.FilmFavEntry.CONTENT_URI + "/" + added);
                break;
            case TV_SHOW:
                added = tvShowsHelper.insertProviderTvShows(values);
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DataContract.TvShowsFavEntry.CONTENT_URI, new TvShowFavFragment.DataObserver(new Handler(), getContext()));
                favoriteUri = Uri.parse(DataContract.TvShowsFavEntry.CONTENT_URI + "/" + added);
                break;
            default:
                added = 0;
                favoriteUri = Uri.parse(DataContract.FilmFavEntry.CONTENT_URI + "/" + added);
                break;
        }
        return favoriteUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        filmHelper.open();
        tvShowsHelper.open();
        int deleted;
        switch (stringUriMatcher.match(uri)) {
            case FILM_ID:
                deleted = filmHelper.deleteProviderFilm(uri.getLastPathSegment());
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DataContract.FilmFavEntry.CONTENT_URI, new FilmFavFragment.DataObserver(new Handler(), getContext()));
                break;
            case TV_SHOW_ID:
                deleted = tvShowsHelper.deleteProviderTvShows(uri.getLastPathSegment());
                Objects.requireNonNull(getContext()).getContentResolver().notifyChange(DataContract.TvShowsFavEntry.CONTENT_URI, new TvShowFavFragment.DataObserver(new Handler(), getContext()));
                break;
            default:
                deleted = 0;
                break;
        }
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
