package la.juju.android.yanwenzilayout.entities;

import java.util.List;

/**
 * Created by HelloVass on 15/11/27.
 */
public class FaceTextGroup {

  public List<FaceTextList> faceTextListGroup;

  private static class FaceTextList {
    List<FaceText> faceTextList;
  }
}
