package la.juju.www.android.ftil.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HelloVass on 15/11/25.
 *
 * 颜文字实体类
 */
public class FaceText implements Parcelable {

  public int id;

  public String content;

  public FaceText() {

  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.id);
    dest.writeString(this.content);
  }

  protected FaceText(Parcel in) {
    this.id = in.readInt();
    this.content = in.readString();
  }

  public static final Creator<FaceText> CREATOR = new Creator<FaceText>() {
    public FaceText createFromParcel(Parcel source) {
      return new FaceText(source);
    }

    public FaceText[] newArray(int size) {
      return new FaceText[size];
    }
  };
}
