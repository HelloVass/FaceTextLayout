package la.juju.android.ftil.source;

import java.util.ArrayList;
import java.util.List;
import la.juju.android.ftil.entities.FaceText;

/**
 * Created by HelloVass on 16/2/26.
 */
public class MemorySource implements FaceTextProvider {

  String[] FACE_TEXT_SOURCE = new String[] {
      "(*^ω^*)", "(o^^o)♪", "(*ﾟ∀ﾟ*)", "＼(☆o☆)／", "(*´∀｀)", "(✪ω✪)", "(✧◡✧)", "o(*≧▽≦)ツ┏━┓[拍桌狂笑!] ",
      "ヽ(´▽｀)/", "→_→", "←w←", "(｡´∀｀)ﾉ", "_(•̀ω•́ 」∠)_", "_(:з」∠)_ ", "(#ﾟДﾟ)", "(　ﾟ皿ﾟ)",
      "щ(゜ロ゜щ)", "(╬⁽⁽ ⁰ ⁾⁾ Д ⁽⁽ ⁰ ⁾⁾)", "（＞д＜）", "(ÒωÓױ)", "(((╹д╹;)))", "ヾ(◍’౪`◍)ﾉﾞ♡",
      "ლ(́◉◞౪◟◉‵ლ)", "(´ε｀ )♡", "✧*｡٩(ˊωˋ*)و✧*｡", "ヾ(´▽｀;)ゝ", "(o˘д˘)o", "(￣^￣)", "（；¬＿¬)",
      "(╯‵□′)╯︵┻━┻", "╰（‵□′）╯", "(▼皿▼#)", "（ ＴДＴ）", "(=ｘェｘ=)", "(T^T)", "( •̥́ ˍ •̀ू )嘤嘤嘤~",
      "(๑ó﹏ò๑)", "m(._.)m", "m(￢0￢)m", "(‘◇’)?", "［(－－)］zzz", "☆～（ゝ。∂）", "_(:з)∠)_",
      "ꉂ ೭(˵¯̴͒ꇴ¯̴͒˵)౨”", "(⌯¤̴̶̷̀ω¤̴̶̷́)✧", "(▭-▭)✧", "(●—●)", "(づ ●─● )づ", "｡ﾟ+.ღ(ゝ◡ ⚈᷀᷁ღ)",
      "(›´ω`‹ )", "( ・᷄ ᵌ・᷅ )", "(๑°3°๑)", " =͟͟͞͞(꒪⌓꒪*)", "( ˘•ω•˘ )", "~( ´•︵•` )~", "o(〃'▽'〃)o",
      "(๑Ő௰Ő๑)", "(˶‾᷄ ⁻̫ ‾᷅˵)", "(•ૢ⚈͒⌄⚈͒•ૢ)", "(σ▰˃̶̀ꇴ˂̶́)σ✧", "((٩(//̀Д/́/)۶))", "(⁄ ⁄•⁄ω⁄•⁄ ⁄)",
      "ᕙ(⇀‸↼‵‵)ᕗ", "(῀( ˙᷄ỏ˙᷅ )῀)ᵒᵐᵍᵎᵎᵎ", "눈_눈", "Ծ‸Ծ", "(ﾉಥ益ಥ)", "( •̥́ ˍ •̀ू )", "(๑‾᷅⍨‾᷅๑)",
      "( ✘_✘ )↯", "(￣ε(#￣)", "(๑ʘ̅ д ʘ̅๑)!!!", "✧＼\\ ٩( 'ω' )و /／✧"
  };

  @Override public List<FaceText> provideFaceTextList() {
    List<FaceText> faceTextList = new ArrayList<>();
    for (String content : FACE_TEXT_SOURCE) {
      FaceText faceText = new FaceText();
      faceText.content = content;
      faceTextList.add(faceText);
    }
    return faceTextList;
  }
}
