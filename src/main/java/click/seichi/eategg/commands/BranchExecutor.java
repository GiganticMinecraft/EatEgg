package click.seichi.eategg.commands;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public abstract class BranchExecutor extends Executor {
  private final Set<Executor> branches;
  private final Executor whenNoBranch;

  protected BranchExecutor(String commandName, Set<Executor> branches) {
    this(commandName, branches, null);
  }

  protected BranchExecutor(String commandName, Set<Executor> branches, Executor whenNoBranch) {
    super(commandName);
    this.branches = branches;
    this.whenNoBranch = whenNoBranch;
  }

  @Override
  public void execute(CommandContext context) {
    Executor executor =
        branches.stream()
            .filter(
                branch ->
                    !context.args().isEmpty()
                        && context
                            .args()
                            .get(0)
                            .equals(branch.getCommandName().toLowerCase(Locale.ROOT)))
            .findFirst()
            .orElseGet(
                () -> whenNoBranch != null ? whenNoBranch : new PrintExecutor(getCommandName()));

    List<String> args =
        context.args().isEmpty() ? List.of() : context.args().subList(1, context.args().size());
    executor.execute(new CommandContext(context.sender(), context.command(), args));
  }

  @Override
  public List<String> completeTab(CommandContext context) {
    return branches.stream()
        .map(branch -> branch.getCommandName().toLowerCase(Locale.ROOT))
        .toList();
  }
}
