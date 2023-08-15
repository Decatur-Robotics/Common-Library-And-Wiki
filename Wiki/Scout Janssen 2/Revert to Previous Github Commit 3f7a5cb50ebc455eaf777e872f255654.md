# Revert to Previous Github Commit

1. Open the sj2 directory in a command prompt, with git installed
2. Use git log to see previous commits, then “q” to quit
3. Use “git reset <commit ID>” to reset to previous commit
4. Use “git push origin <branch> -f” to force a commit to github, making the most recent commit the one you reverted back to using git reset.