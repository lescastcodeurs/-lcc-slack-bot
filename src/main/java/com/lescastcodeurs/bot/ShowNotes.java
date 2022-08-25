package com.lescastcodeurs.bot;

import com.lescastcodeurs.bot.slack.SlackThread;
import io.quarkus.qute.TemplateData;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@TemplateData
public class ShowNotes {

  private final LocalDateTime now;
  private final Locale locale;
  private final List<ShowNote> notes;

  public ShowNotes(List<SlackThread> threads) {
    this.now = LocalDateTime.now();
    this.locale = Locale.FRANCE;
    this.notes =
        threads.stream()
            .filter(SlackThread::isUserMessage)
            .filter(ShowNote::isShowNote)
            .map(ShowNote::new)
            .toList();
  }

  public LocalDateTime now() {
    return now;
  }

  public Locale locale() {
    return locale;
  }

  public List<ShowNote> notes() {
    return notes;
  }

  public List<ShowNote> notes(String label) {
    ShowNoteCategory category =
        ShowNoteCategory.find(label)
            .orElseThrow(
                () -> new IllegalArgumentException("no category found for label " + label));
    return notes().stream().filter(n -> n.category() == category).toList();
  }
}
