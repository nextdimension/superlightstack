package com.sls.superlight.superlightstack;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

public class Slate implements Parcelable {

    private int layoutID;
    private Bundle bundle;
    private int id;
    private SparseArray<Parcelable> viewHierarchyState = new SparseArray<>();

    public Slate(int layoutID) {
        this.layoutID = layoutID;
    }

    public Slate(int layoutID, Bundle bundle, int id) {
        this.layoutID = layoutID;
        this.bundle = bundle;
        this.id = id;
    }
    public Bundle getBundle() {
        return bundle;
    }
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
    public int getLayoutID() {
        return layoutID;
    }
    public void setLayoutID(int layoutID) {
        this.layoutID = layoutID;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public SparseArray<Parcelable> getContainer() {
        return viewHierarchyState;
    }
    public void setContainer(SparseArray<Parcelable> viewHierarchyState) {
        this.viewHierarchyState = viewHierarchyState;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.layoutID);
        dest.writeBundle(this.bundle);
        dest.writeInt(this.id);
        dest.writeSparseArray((SparseArray) this.viewHierarchyState);
    }

    protected Slate(Parcel in) {
        this.layoutID = in.readInt();
        this.bundle = in.readBundle();
        this.id = in.readInt();
        this.viewHierarchyState = in.readSparseArray(Parcelable.class.getClassLoader());
    }

    public static final Creator<Slate> CREATOR = new Creator<Slate>() {
        @Override
        public Slate createFromParcel(Parcel source) {
            return new Slate(source);
        }

        @Override
        public Slate[] newArray(int size) {
            return new Slate[size];
        }
    };
}
