package la.juju.android.yanwenzilayout;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HelloVass on 15/11/25.
 *
 * 颜文字实体类
 *
 */
public class YanWenZi implements Parcelable {

  public int id;

  public String content;

  public YanWenZi() {

  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.content);
    dest.writeInt(this.id);
  }

  protected YanWenZi(Parcel in) {
    this.content = in.readString();
    this.id = in.readInt();
  }

  public static final Parcelable.Creator<YanWenZi> CREATOR = new Parcelable.Creator<YanWenZi>() {
    public YanWenZi createFromParcel(Parcel source) {
      return new YanWenZi(source);
    }

    public YanWenZi[] newArray(int size) {
      return new YanWenZi[size];
    }
  };
}
