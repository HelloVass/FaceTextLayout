package la.juju.android.yanwenzilayout.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HelloVass on 15/11/25.
 *
 * 颜文字实体类
 */
public class FaceText implements Parcelable {

  public int id;

  public int row;

  public int column;

  public String content;

  public FaceText() {

  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.content);
    dest.writeInt(this.row);
    dest.writeInt(this.column);
  }

  protected FaceText(Parcel in) {
    this.id = in.readInt();
    this.content = in.readString();
    this.row = in.readInt();
    this.column = in.readInt();
  }

  public static final Parcelable.Creator<FaceText> CREATOR = new Parcelable.Creator<FaceText>() {
    public FaceText createFromParcel(Parcel source) {
      return new FaceText(source);
    }

    public FaceText[] newArray(int size) {
      return new FaceText[size];
    }
  };
}
