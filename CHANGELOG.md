# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]
### Added

## [1.3.2] - 2018-08-12
### Fixed
- Fixed slf4j binding compile-time dependency.

## [1.3.1] - 2018-08-09
### Added
- New API to query/delete/modify A and AAAA records based on IPv4/v6.
- More test cases.

## [1.3.0] - 2018-07-28
### Added
- Querying `CNAME` based on `canonical` name.
- `TXT` record support.
- More test cases.

## [1.2.4] - 2018-07-24
### Changed
- Made all search query case insensitive by default except for `AuthZone` & `ZoneDelegation`.

## [1.2.3] - 2018-07-23
### Added
- Added pagination for querying zone delegation.
- Added PTR domain name modify operations. 

## [1.2.2] - 2018-07-21
### Added
- Added API to modify cname canonical name.

## [1.2.1] - 2018-07-17
### Added
- `TTL` support.
- Added support for many new DNS record types.

## [1.1.0] - 2018-04-17
### Added
- Disabled SNI TLS extension.
- `MX` record APIs.
- New `A` record api to query based on IP address.


### Changed
- Dependencies updated to latest versions.
- Upgraded to maven `3.5.3`.

### Removed

### Fixed
- Dig utility to resolve dns records.

## [1.0.1] - 2018-01-19
### Changed
- Minor fixes and cosmetic changes.


## [1.0.0] - 2018-01-18
### Added
- Added unit tests for all record types.
- `Dig` utility for unit test.

### Fixed
- Misc bug fixes.


## [0.0.1] - 2018-01-09
### Added
- Infoblox java api initial release.

[Unreleased]: https://gecgithub01.walmart.com/oneops/infoblox-java/compare/infoblox-java-1.3.2...HEAD
[1.3.2]: https://gecgithub01.walmart.com/oneops/infoblox-java/compare/infoblox-java-1.3.1...infoblox-java-1.3.2
[1.3.1]: https://gecgithub01.walmart.com/oneops/infoblox-java/compare/infoblox-java-1.3.0...infoblox-java-1.3.1
[1.3.0]: https://gecgithub01.walmart.com/oneops/infoblox-java/compare/infoblox-java-1.2.4...infoblox-java-1.3.0
[1.2.4]: https://gecgithub01.walmart.com/oneops/infoblox-java/compare/infoblox-java-1.2.3...infoblox-java-1.2.4
[1.2.3]: https://gecgithub01.walmart.com/oneops/infoblox-java/compare/infoblox-java-1.2.2...infoblox-java-1.2.3
[1.2.2]: https://gecgithub01.walmart.com/oneops/infoblox-java/compare/infoblox-java-1.2.1...infoblox-java-1.2.2
[1.2.1]: https://gecgithub01.walmart.com/oneops/infoblox-java/compare/release-1.1.0...infoblox-java-1.2.1
[1.1.0]: https://gecgithub01.walmart.com/oneops/infoblox-java/compare/release-1.0.1...release-1.1.0
[1.0.1]: https://gecgithub01.walmart.com/oneops/infoblox-java/compare/release-1.0.0...release-1.0.1
[1.0.0]: https://gecgithub01.walmart.com/oneops/infoblox-java/compare/release-0.0.1...release-1.0.0
[0.0.1]: https://gecgithub01.walmart.com/oneops/infoblox-java/compare/release-0.0.1...release-0.0.1
