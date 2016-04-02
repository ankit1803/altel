# How to contribute
We would love to have your help. Before you start working however, please read
and follow this short guide.

# Translation
Please go to our [translation website](http://translate.jitsi.org), which is a
[Pootle](http://pootle.translatehouse.org/) instance that allows for easy
online translation. Many languages are already there and wait for completion.

If your preferred language is not yet created, please drop us an
[e-mail](mailto:dev@jitsi.org). Be aware though that completing a new language
will be quite some effort, so be sure you actually want to take up on that
work and translate more than a few words.

Please do not create any pull requests for the resources_XX.properties files,
they will be overwritten with what comes from
[Pootle](http://translate.jitsi.org). The only exception is if you find
mistakes in the language file for
[English](https://github.com/jitsi/jitsi/blob/master/resources/languages/resources.properties).

For some more information, e.g. how to translate offline visit the
[How To Translate Jitsi](https://jitsi.org/Documentation/HowToTranslateSIPCommunicator)
on our website.

# Code contributions
Found a bug and know how to fix it? Great! Please read on.

## Contributor License Agreement
While Jitsi is released under the
[LGPL](https://github.com/jitsi/jitsi/blob/master/LICENSE), the copyright
holder and principal creator is [Blue Jimp](http://www.bluejimp.com/). To
ensure that we can continue making Jitsi available under an open source license
and still pay our bills, we need you to sign our
[contributor license agreement](http://www.bluejimp.com/bca.pdf). If you
cannot accept the terms laid out in the agreement, unfortunately, we cannot
accept your contribution.

## Coding Rules
- Please read and follow the [code conventions](https://jitsi.org/Documentation/CodeConvention),
  especially the limit on 80 characters per line.
- Do not reformat existing code.

## Creating Pull Requests
- Perform **one** logical change per pull request.
- Maintain a clean list of commits, squash them if necessary.
- Rebase your topic branch on top of the master branch before creating the pull request.
